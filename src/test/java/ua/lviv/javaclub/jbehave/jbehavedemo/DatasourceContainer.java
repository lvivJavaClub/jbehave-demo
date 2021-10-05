package ua.lviv.javaclub.jbehave.jbehavedemo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.MySQLContainer;

@Slf4j
public class DatasourceContainer extends MySQLContainer<DatasourceContainer> {
	private static final Object LOCK = new Object();
	private static final String IMAGE_VERSION = "mysql:5.7.12";

	private static DatasourceContainer container;

	private DatasourceContainer() {
		super(IMAGE_VERSION);
	}

	public static DatasourceContainer getInstance() {
		if (container != null) {
			return container;
		}
		synchronized (LOCK) {
			if (container == null) {
				log.debug("Creating image: {}", IMAGE_VERSION);
				container = new DatasourceContainer();
			}
			return container;
		}
	}

	@SneakyThrows
	@Override
	public void start() {
		log.debug("Starting container id: {}", container.getContainerId());
		super.start();
	}

	@Override
	public void stop() {
		log.debug("Stopping container id: {}", container.getContainerId());
	}
}
