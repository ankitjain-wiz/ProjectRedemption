package com.jpa.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jpa.data.all.Student;
import com.jpa.data.all.StudentRepository;

@SpringBootApplication
public class SpringwithJpaApplication implements CommandLineRunner {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringwithJpaApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		//logger.info("Student id 10001 -> {}", repository.findById(10001L));
		
		logger.info("All users 1 -> {}", repository.findAll());
		
		//Insert
		logger.info("Inserting -> {}", repository.save(new Student("John", "A12346578")));

		//Update
		logger.info("Update 10001 -> {}", repository.save(new Student(10001L, "Name-Updated", "New-Passport2")));

		//repository.deleteById(10002L);
		repository.delete(9l);
		
		logger.info("All users 2 -> {}", repository.findAll());
	}
	
	/*public static void main(final String[] args) throws JsonParseException, JsonMappingException, IOException {
		//SpringApplication.run(PaymentWebserviceApp.class, args);
		
		ApplicationContext app = SpringApplication.run(PaymentWebserviceApp.class, args);
		ReimbursementService service = app.getBean(ReimbursementService.class);
		List<ReimbursementEntity> reimbursementEntityList = service.getReimbursementIdByPaymentId(Long.parseLong("598010"));
		//List<ReimbursementEntity> reimbursementEntityList2 = service.findAll();
		ReimbursementEntity reimbursementEntityList3 = service.findOne(2l);
		List<ReimbursementEntity> reimbursementEntityList4 = service.findQueryTypeOne(8L, 597068l);
		List<ReimbursementEntity> reimbursementEntityList5 = service.findQueryTypeTwo(2l, 15l);
		//List<ReimbursementEntity> reimbursementEntityList6 = service.findQueryTypeThree(2l);
		List<ReimbursementEntity> reimbursementEntityList7 = service.selectQueryTypeOne(7234l);
		
		List<ReimbursementEntity> reimbursementEntityList8 = service.getReimbursementIDByPayerID(597068l);
		
		System.out.println(reimbursementEntityList.size());
		//System.out.println(reimbursementEntityList2.size());
		System.out.println(reimbursementEntityList4.size());
		System.out.println(reimbursementEntityList5.size());
		//System.out.println(reimbursementEntityList6.size());
		System.out.println(reimbursementEntityList7.get(0).getReimbursementId());
		System.out.println(reimbursementEntityList3.getReimbursementId());
		System.out.println(reimbursementEntityList8.get(0).getReimbursementId());
		
		//sendTestEvent(args);
	}*/


}
