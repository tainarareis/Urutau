package com.modesteam.urutau.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.GenericDAO;
import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.persistence.DuplicateSortException;
import com.modesteam.urutau.persistence.Finder;
import com.modesteam.urutau.persistence.FinderAdapter;
import com.modesteam.urutau.persistence.Order;
import com.modesteam.urutau.persistence.OrderEnum;
import com.modesteam.urutau.persistence.OrderOption;

public class RequirementService implements Finder<Artifact>, Order<Artifact> {
	private static final Logger logger = LoggerFactory.getLogger(RequirementService.class);

	private RequirementDAO requirementDAO;
	private OrderOption orderOption;

	public RequirementService() {
		this(null);
	}

	@Inject
	public RequirementService(RequirementDAO requirementDAO) {
		this.requirementDAO = requirementDAO;
	}

	public void save(Artifact requirement) {
		requirementDAO.create(requirement);
	}

	/**
	 * Returns a requirement caught by title that have a certain id
	 * 
	 * @param id
	 *            unique
	 * @param title
	 *            name of Requirement, an usual identifier, but not unique
	 * @return a requirement
	 */
	public Artifact getRequirement(int id, String title) throws UnsupportedEncodingException {
		assert (id < 0 || title == null);

		Artifact requirement = requirementDAO.find(new Long(id));

		logger.info("Decoded title is " + title);

		// Compares decoded title with instance of database
		if (requirement.getTitle().equalsIgnoreCase(title)) {
			return requirement;
		} else {
			throw new IllegalArgumentException("This requirement not exist");
		}
	}

	/**
	 * See {@link GenericDAO#destroy(Object)}
	 * 
	 * @return true if delete was complete, without errors
	 */
	public void delete(Artifact requirement) {
		Artifact requirementToDelete = requirementDAO.find(requirement.getId());

		if (requirementToDelete != null) {
			requirementDAO.destroy(requirementToDelete);
		} else {
			throw new IllegalArgumentException("This requirement not exist");
		}
	}

	/**
	 * Verifies the existence of a requirement by its id
	 * 
	 * @param requirementId
	 * @return true if the requirement exists
	 */
	public boolean verifyExistence(long requirementId) {

		logger.info("Verifying the requirement existence in database.");

		Artifact requirement = null;
		try {
			requirement = requirementDAO.get("id", requirementId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (requirement == null) {
			logger.info("The requirement is null");
			return false;
		} else {
			logger.info("The requirement isn't null");
			return true;
		}
	}

	public void create(Artifact requirement) {
		requirementDAO.create(requirement);
	}

	/**
	 * @param artifact
	 * @return
	 */
	public boolean update(Artifact requirement) {
		return requirementDAO.update(requirement) != null;
	}

	/**
	 * Captures a unique requirement based on its id
	 * 
	 * @param id
	 *            of the requirement
	 * @return a requirement
	 */
	public Artifact getByID(Long id) {

		logger.info("Starting DAO search.");

		Artifact requirement = requirementDAO.find(id);

		if (requirement != null) {
			logger.info("RequirementDAO returned zero requirements.");
			return requirement;
		} else {
			throw new IllegalArgumentException("The requirement does not exist.");
		}
	}

	@Override
	public boolean exists(Long id) {
		return false;
	}

	@Override
	public Artifact find(Long id) {
		return null;
	}

	@Override
	public List<Artifact> findBy(String field, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Artifact> where(String conditions) {
		String sql = "SELECT requirement FROM " + Artifact.class.getName() + " requirement WHERE ";

		sql = sql.concat(conditions);

		return requirementDAO.findUsing(sql);
	}

	@Override
	public Order<Artifact> asc(String field) {
		if (orderOption == null) {
			this.orderOption = new OrderOption(OrderEnum.ASC, field);
		} else {
			throw new DuplicateSortException(
					"Option sort should be configured one time, you probably is calling"
							+ " something like desc(field).asc(field).");
		}
		return this;
	}

	@Override
	public Order<Artifact> desc(String field) {
		if (orderOption == null) {
			this.orderOption = new OrderOption(OrderEnum.DESC, field);
		} else {
			throw new DuplicateSortException(
					"Option sort should be configured one time, you probably is calling"
							+ " something like desc(field).asc(field).");
		}
		return this;
	}

	@Override
	public Order<Artifact> between(Long first, Long last) {
		this.orderOption.setStart(first.toString());
		this.orderOption.setEnd(last.toString());
		return this;
	}

	@Override
	public FinderAdapter<Artifact> find() {
		FinderAdapter<Artifact> adapter = new FinderAdapter<Artifact>(this, orderOption);
		// clean orderOption to others
		orderOption = null;

		return adapter;
	}

}
