package com.modesteam.urutau.database;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.model.system.setting.SystemSetting;
import com.modesteam.urutau.model.system.setting.SystemSettingContext;

/**
 * TODO I believe that should have a better way to create this objects
 */
@ApplicationScoped
public class BasicDataCreator {

	private static final Logger logger = LoggerFactory.getLogger(BasicDataCreator.class);
	
    @Inject
	private EntityManagerFactory factory;
    
    private EntityManager manager;
    
	private List<Layer> defaultLayers = new ArrayList<Layer>();
	
	/**
	 * When {@link ServletContext} has loaded it needed
	 * whether not exists, fills database with:
	 * 
	 * <ul>
	 * 	<li>Default system settings {@link SystemSetting}</li>
	 * 	<li>Default layers</li>
	 * </ul>
	 */
	public void init(@Observes ServletContext context) {
		
    	logger.info("Preparing database to support system...");

    	try { 
    		manager = factory.createEntityManager();
	    	EntityTransaction transaction = manager.getTransaction();
	    	
	    	transaction.begin();
			
			createSystemSettings();
			createDefaultLayer();
				
			transaction.commit();
    	} catch (IllegalStateException exception) {
    		logger.error("Something fails with manager");
    	} catch (RollbackException rollbackException) {
    		logger.error("Commit fails when BasicDataCreator tried persist basic data", rollbackException);
    	}
		
    	if(manager.isOpen()) {
    		manager.close();
    	}
    	
    	logger.info("The basic data where created");
	}
	
	@PreDestroy
	public void destroy(EntityManagerFactory factory) {
		if(factory.isOpen()) {
			factory.close();
		}
		
		this.defaultLayers.clear();
	}

	private void createSystemSettings() {
		if(!existsSystemSettings()) {
			for(SystemSettingContext context : SystemSettingContext.values()) {
				manager.persist(new SystemSetting(context));
			}
		} else {
			logger.info("Settings already created...");
		}
	}

	private boolean existsSystemSettings() {
		return manager.find(SystemSetting.class, 1L) != null;
	}

	private void createDefaultLayer() {
		// If there is no layer, then create it
		if (!existsLayer()) {
			// populate layers
			fill();

			for (Layer layer : defaultLayers) {
				manager.persist(layer);
			}
		} else {
			logger.info("The Default layers are already created...");
		}
	}
	
	private boolean existsLayer() {
		return manager.find(Layer.class, 1L) != null;
	}

	/**
	 * TODO refactor this
	 */
	private void fill() {
		Layer layer1 = new Layer();
		layer1.setName("Backlog");
		layer1.setDescription("Come on");
		defaultLayers.add(layer1);

		Layer layer2 = new Layer();
		layer2.setName("In Progress");
		layer2.setDescription("Hey, ok, i am doing!");
		defaultLayers.add(layer2);

		Layer layer3 = new Layer();
		layer3.setName("Testing");
		layer3.setDescription("It is right, but the computer can not understand me...");
		defaultLayers.add(layer3);

		Layer layer4 = new Layer();
		layer4.setName("Done");
		layer4.setDescription("I won, i won! Yeah...");
		defaultLayers.add(layer4);

	}
}
