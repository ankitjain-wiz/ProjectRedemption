package org.epo.cms.edfs.services.common.useraccount;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.externalapi.ExternalApiService;
import org.epo.cms.edfs.services.common.util.ListUtils;

/**
 * A {@link AbstractPagedServiceRetrievalExecutor} that uses threading to improve the speed of
 * retrieval of the different pages.
 * 
 * @author PV53311
 *
 */
public class ThreadedPagedServiceRetrievalExecutor extends AbstractPagedServiceRetrievalExecutor {

  private static final Logger LOGGER = LogManager.getLogger();

  private final int numberOfThreads;


  /**
   * Constructor for the ThreadedPagedServiceRetrievalExecutor.
   * 
   * @param apiService - The {@link ExternalApiService} to use for invoking the external api.
   * @param pageParamName - The name of the page indicator parameter
   * @param pageSizeParamName - The name of the pageSize parameter
   * @param pageSizeValue - The value to use for the pageSize parameter
   * @param numberOfThreads - The numbe of threads to use for retrrieval.
   */
  public ThreadedPagedServiceRetrievalExecutor(final ExternalApiService apiService,
      final String pageParamName, final String pageSizeParamName, final int pageSizeValue,
      final int numberOfThreads) {
    super(apiService, pageParamName, pageSizeParamName, pageSizeValue);
    this.numberOfThreads = numberOfThreads;
  }


  /**
   * {@inheritDoc}.
   */
  @Override
  protected List<String> retrieveRemainingPages(final String apiHost, final String baseApiUrl,
      final int fromPage, final int toPage) throws CustomException {

    LOGGER.debug("Retrieving pages using Threaded Retriever.");

    List<Integer> pagesToRetrieve = ListUtils.listOfIntegers(fromPage, toPage);
    List<List<Integer>> chunkedPages = ListUtils.chopIntoParts(pagesToRetrieve, numberOfThreads);

    ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
    List<FutureTask<List<String>>> retrieverTasks =
        createRetrieveTasks(apiHost, baseApiUrl, chunkedPages, executor);
    LOGGER.debug("Created the retrieval Tasks {}.", retrieverTasks.size());

    // Wait for completion
    waitForAllTasksToComplete(retrieverTasks);

    // Retrieve Results
    List<String> remainingResponses = aggregateRetrievalResults(retrieverTasks);
    executor.shutdown();

    return remainingResponses;
  }


  private List<FutureTask<List<String>>> createRetrieveTasks(final String apiHost,
      final String baseApiUrl, final List<List<Integer>> chunkedPages,
      final ExecutorService executor) {
    List<FutureTask<List<String>>> tasks = new ArrayList<>();

    for (int executorCnt = 0; executorCnt < chunkedPages.size(); executorCnt++) {
      FutureTask<List<String>> task = new FutureTask<>(new DefinedPageSetServiceCaller(executorCnt,
          apiHost, baseApiUrl, chunkedPages.get(executorCnt)));
      executor.execute(task);
      LOGGER.debug("Created and started the retrieval Task {}.", executorCnt);
      tasks.add(task);
    }

    return tasks;
  }

  private void waitForAllTasksToComplete(final List<FutureTask<List<String>>> retrieverTasks) {
    while (true) {
      boolean allTasksCompleted = true;
      for (int cnt = 0; cnt < retrieverTasks.size(); cnt++) {
        boolean specificTaskCompleted = retrieverTasks.get(cnt).isDone();
        allTasksCompleted = allTasksCompleted && specificTaskCompleted;
      }

      if (allTasksCompleted) {
        LOGGER.info("All retrieval tasks have completed.");
        break;
      }
    }
  }

  private List<String> aggregateRetrievalResults(
      final List<FutureTask<List<String>>> retrieverTasks) {
    List<String> remainingReponses = new ArrayList<>();

    for (int cnt = 0; cnt < retrieverTasks.size(); cnt++) {
      try {
        List<String> retrievalResults = retrieverTasks.get(cnt).get();
        LOGGER.info("Retriever task {} retrieved {} results.", cnt, retrievalResults.size());
        remainingReponses.addAll(retrievalResults);
      } catch (InterruptedException | ExecutionException e) {
        LOGGER.debug("Retrieved results from retriever task {} - General Exception.", cnt);
        LOGGER.error("Exception occured during aggreagting of results.", e);
      }
    }

    return remainingReponses;
  }



  /**
   * The DefinedPageSetServiceCall implements the {@link Callable} interfeace and as such can be
   * used within {@link FutureTask}. This class is responsible for retrieving the specified pages
   * from the external service.
   * 
   * @author PV53311
   *
   */
  class DefinedPageSetServiceCaller implements Callable<List<String>> {

    private final int executorId;
    private final List<Integer> pagesToRetrieve;
    private final String apiHost;
    private final String baseUrl;


    /**
     * Constructor for the DefinedPageSetServiceCaller
     * 
     * @param executorId - Identifier for the executor.
     * @param apiHost - The identifier for the API Host.
     * @param baseUrl - The base API url, this url does not yet contain the paging parameter.
     * @param pagesToRetrieve - The list of page number s that this instance needs to retrieve.
     */
    public DefinedPageSetServiceCaller(final int executorId, final String apiHost,
        final String baseUrl, final List<Integer> pagesToRetrieve) {
      this.executorId = executorId;
      this.apiHost = apiHost;
      this.baseUrl = baseUrl;
      this.pagesToRetrieve = new ArrayList<>(pagesToRetrieve);
    }

    @Override
    public List<String> call() throws Exception {
      List<String> results = new ArrayList<>();

      for (int cnt = 0; cnt < pagesToRetrieve.size(); cnt++) {
        int pageToCall = pagesToRetrieve.get(cnt);
        LOGGER.info("Executing from executor: {} - Page: {}.", executorId, pageToCall);
        try {
          String response = retrieveSinglePage(apiHost, baseUrl, pageToCall);
          results.add(response);
          Thread.sleep(50);
        } catch (CustomException e) {
          LOGGER.debug("Executing from executor: {} - Page: {} - Exception.", executorId,
              pageToCall);
          LOGGER.error("Executing from executor.", e);
        }
      }

      LOGGER.info("Executing from executor: {} - NumberOfresults: {}.", executorId, results.size());
      return results;
    }

  }
}
