package com.modesteam.urutau.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.GenericDAO;
import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.exception.NotImplementedError;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.persistence.DuplicateSortException;
import com.modesteam.urutau.persistence.Finder;
import com.modesteam.urutau.persistence.FinderAdapter;
import com.modesteam.urutau.persistence.Order;
import com.modesteam.urutau.persistence.OrderEnum;
import com.modesteam.urutau.persistence.OrderOption;
import com.modesteam.urutau.persistence.Persistence;

public class RequirementService
		implements Persistence<Artifact>, Finder<Artifact>, Order<Artifact> {
	private static final Logger logger = LoggerFactory.getLogger(RequirementService.class);

	private RequirementDAO requirementDAO;
	private OrderOption orderOption;
	
	/**
	 * @deprecated CDI only
	 */
	public RequirementService() {
		this(null);
	}

	@Inject
	public RequirementService(RequirementDAO requirementDAO) {
		this.requirementDAO = requirementDAO;
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
	public Artifact getBy(Long id, String title) {
		Artifact requirement = find(id);

		if (requirement.getTitle() == title) {
			throw new IllegalArgumentException("This title not matches");
		}

		return requirement;
	}

	@Override
	public void reload(Artifact entity) {
		// TODO Auto-generated method stub
		throw new NotImplementedError();
	}

	@Override
	public void update(Artifact entity) {
		requirementDAO.update(entity);
	}

	@Override
	public void save(Artifact entity) {
		requirementDAO.create(entity);
	}

	/**
	 * See {@link GenericDAO#destroy(Object)}
	 * 
	 * @return true if delete was complete, without errors
	 */
	@Override
	public void delete(Artifact requirement) {
		Artifact requirementToDelete = requirementDAO.find(requirement.getId());

		if (requirementToDelete != null) {
			requirementDAO.destroy(requirementToDelete);
		} else {
			throw new IllegalArgumentException("This requirement not exist");
		}
	}

	@Override
	public boolean exists(Long id) {
		logger.debug("Verifying the requirement existence in database.");

		Artifact requirement = requirementDAO.find(id);

		if (requirement == null) {
			logger.debug("The requirement is null");
		} else {
			logger.debug("The requirement isn't null");
		}

		return requirement != null;
	}

	@Override
	public Artifact find(Long id) {
		return requirementDAO.find(id);
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
