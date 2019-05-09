package org.epo.cms.edfs.services.casesampling.dao.impl;

import org.epo.cms.edfs.services.casesampling.dao.DirectorateWorkloadSettingDao;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.casesampling.util.DateTime;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.DirectorateWorkloadSetting;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

/**
 * Implementation of DirectorateWorkloadSettingDao.
 * 
 * @author amigarg
 *
 */
@Repository
public class DirectorateWorkloadSettingDaoImpl extends GenericDaoImpl<DirectorateWorkloadSetting, Integer>
		implements DirectorateWorkloadSettingDao {

	@Autowired
	ResponseValidator responseValidator;

	@Autowired
	private UpdaterSettingsResponse updaterSettingsResponse;

	@Autowired
	private DateTime dateTime;

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public DirectorateWorkloadSettingDto workloadSettingEntityDtoMapper(DirectorateWorkloadSetting workloadSetting) {
		DirectorateWorkloadSettingDto workloadSettingDto = new DirectorateWorkloadSettingDto();
		workloadSettingDto.setAprActualCount(workloadSetting.getAprActualCount());
		workloadSettingDto.setAprWorkLoadCount(workloadSetting.getAprWorkLoadCount());
		workloadSettingDto.setAugActualCount(workloadSetting.getAugActualCount());
		workloadSettingDto.setAugWorkLoadCount(workloadSetting.getAugWorkLoadCount());
		workloadSettingDto.setDecActualCount(workloadSetting.getDecActualCount());
		workloadSettingDto.setDecWorkLoadCount(workloadSetting.getDecWorkLoadCount());
		workloadSettingDto.setDirectorateWorkloadSettingId(workloadSetting.getDirectorateWorkloadSettingId());
		workloadSettingDto.setFebActualCount(workloadSetting.getFebActualCount());
		workloadSettingDto.setFebWorkLoadCount(workloadSetting.getFebWorkLoadCount());
		workloadSettingDto.setJanActualCount(workloadSetting.getJanActualCount());
		workloadSettingDto.setJanWorkLoadCount(workloadSetting.getJanWorkLoadCount());
		workloadSettingDto.setJulActualCount(workloadSetting.getJulActualCount());
		workloadSettingDto.setJulWorkLoadCount(workloadSetting.getJulWorkLoadCount());
		workloadSettingDto.setJunActualCount(workloadSetting.getJunActualCount());
		workloadSettingDto.setJunWorkLoadCount(workloadSetting.getJunWorkLoadCount());
		workloadSettingDto.setMarActualCount(workloadSetting.getMarActualCount());
		workloadSettingDto.setMarWorkLoadCount(workloadSetting.getMarWorkLoadCount());
		workloadSettingDto.setMaxTarget(workloadSetting.getMaxTarget());
		workloadSettingDto.setMayActualCount(workloadSetting.getMayActualCount());
		workloadSettingDto.setMayWorkLoadCount(workloadSetting.getMayWorkLoadCount());
		workloadSettingDto.setNovActualCount(workloadSetting.getNovActualCount());
		workloadSettingDto.setNovWorkLoadCount(workloadSetting.getNovWorkLoadCount());
		workloadSettingDto.setNumExamInDir(workloadSetting.getNumExamInDir());
		workloadSettingDto.setOctActualCount(workloadSetting.getOctActualCount());
		workloadSettingDto.setOctWorkLoadCount(workloadSetting.getOctWorkLoadCount());
		workloadSettingDto.setSepActualCount(workloadSetting.getSepActualCount());
		workloadSettingDto.setSepWorkLoadCount(workloadSetting.getSepWorkLoadCount());
		workloadSettingDto.setYear(workloadSetting.getYear());

		return workloadSettingDto;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public int updateDirectorateWorkloadSetting(DirectorateWorkloadSettingDto directorateWorkloadSettingDto,
			long directorateId, int year) throws CustomException {
		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq("directorate.directorateId", directorateId));
		criteria.add(Restrictions.eq("year", year));
		DirectorateWorkloadSetting directorateWorkloadSetting = (DirectorateWorkloadSetting) criteria.uniqueResult();

		if (!ObjectUtils.isEmpty(directorateWorkloadSetting)) {
			directorateWorkloadSetting.setDirectorate(directorateWorkloadSettingDto.getDirectorate());
			monthValidation(directorateWorkloadSetting, directorateWorkloadSettingDto);
			saveOrUpdate(directorateWorkloadSetting);
		} else {
			updaterSettingsResponse.setDescription("No data in DirectorateWorkloadSetting");
			throw new CustomException(responseValidator.getErrorResponse("0",
					"No data in DirectorateWorkloadSetting for directorid=" + directorateId));
		}
		return 1;
	}

	/**
	 * @param directorateWorkloadSetting
	 *            : DirectorateWorkloadSetting
	 * @param directorateWorkloadSettingDto
	 *            : DirectorateWorkloadSettingDto
	 */
	private void monthValidation(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		
		switch (dateTime.getMonthNumber()) {
		case 1:
			updateJan(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 2:

			updateFeb(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 3:

			updateMar(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 4:

			updateApr(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 5:
			updateMay(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 6:
			updateJun(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 7:
			updateJul(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 8:
			updateAug(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 9:
			updateSep(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 10:
			updateOct(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 11:
			updateNov(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		case 12:
			updateDec(directorateWorkloadSetting, directorateWorkloadSettingDto);
			break;

		default:
			break;

		}
	}

	private void updateDec(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setDecWorkLoadCount(directorateWorkloadSettingDto.getDecWorkLoadCount());
	}

	private void updateNov(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setNovWorkLoadCount(directorateWorkloadSettingDto.getNovWorkLoadCount());
		updateDec(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	private void updateOct(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setOctWorkLoadCount(directorateWorkloadSettingDto.getOctWorkLoadCount());
		updateNov(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	private void updateSep(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setSepWorkLoadCount(directorateWorkloadSettingDto.getSepWorkLoadCount());
		updateOct(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	private void updateAug(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setAugWorkLoadCount(directorateWorkloadSettingDto.getAugWorkLoadCount());
		updateSep(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	private void updateJul(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setJulWorkLoadCount(directorateWorkloadSettingDto.getJulWorkLoadCount());
		updateAug(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	private void updateJun(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setJunWorkLoadCount(directorateWorkloadSettingDto.getJunWorkLoadCount());
		updateJul(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	private void updateMay(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setMayWorkLoadCount(directorateWorkloadSettingDto.getMayWorkLoadCount());
		updateJun(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	private void updateApr(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setAprWorkLoadCount(directorateWorkloadSettingDto.getAprWorkLoadCount());
		updateMay(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	private void updateMar(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setMarWorkLoadCount(directorateWorkloadSettingDto.getMarWorkLoadCount());
		updateApr(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	private void updateFeb(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setFebWorkLoadCount(directorateWorkloadSettingDto.getFebWorkLoadCount());
		updateMar(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	private void updateJan(DirectorateWorkloadSetting directorateWorkloadSetting,
			DirectorateWorkloadSettingDto directorateWorkloadSettingDto) {
		directorateWorkloadSetting.setJanWorkLoadCount(directorateWorkloadSettingDto.getJanWorkLoadCount());
		updateFeb(directorateWorkloadSetting, directorateWorkloadSettingDto);
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public int checkNumExamInDirValidation(long directorateId, int numMinYearTarget, int sum, int year)
			throws CustomException {
		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq("directorate.directorateId", directorateId));
		criteria.add(Restrictions.eq("year", year));
		DirectorateWorkloadSetting directorateWorkloadSetting = (DirectorateWorkloadSetting) criteria.uniqueResult();

		int numExamInDir;
		if (!ObjectUtils.isEmpty(directorateWorkloadSetting)) {
			numExamInDir = directorateWorkloadSetting.getNumExamInDir();
		} else {
			throw new CustomException(responseValidator.getErrorResponse("0",
					"No data in DirectorateWorkloadSetting for directorid=" + directorateId));
		}

		int minTotal = numExamInDir * numMinYearTarget;
		if (minTotal > sum) {

			updaterSettingsResponse.setDescription("Workloads Count sum  is less than minimum");
			throw new CustomException(
					responseValidator.getErrorResponse("0", "Workloads Count sum  is less than minimum"));
		}

		return 1;
	}

}
