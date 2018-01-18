package hu.gambino.data;

import java.util.ArrayList;

public interface DataProvider {

	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> runQuery(String queryString) throws Exception;

	public ArrayList<CV> getAllCVs() throws Exception;

	public ArrayList<Education> getEducations(Long id) throws Exception;

	public ArrayList<Job> getJobs(Long id) throws Exception;

	public void addCV(CV cv) throws Exception;

	public void addEducation(Education education) throws Exception;

	public void addJob(Job job) throws Exception;

	public void updateCV(CV cv) throws Exception;

	public void updateEducation(Education education) throws Exception;

	public void updateJob(Job job) throws Exception;

	public void deleteCV(CV cv) throws Exception;

	public void deleteEducation(Education education) throws Exception;

	public void deleteJob(Job job) throws Exception;

}
