package net.pms.configuration;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PmsConfiguration {
	private static final String CONFIGURATION_FILENAME = "PMS.conf";

	private static final String KEY_TEMP_FOLDER_PATH = "temp";

	private final Configuration configuration;
	private final TempFolder tempFolder;
	private final ProgramPathDisabler programPaths;

	public PmsConfiguration() throws ConfigurationException, IOException {
		configuration = new PropertiesConfiguration(new File(CONFIGURATION_FILENAME));
		tempFolder = new TempFolder(configuration.getString(KEY_TEMP_FOLDER_PATH));
		programPaths = createProgramPathsChain(configuration);
	}

	/**
	 * Check if we have disabled something first, then check the Windows registry, 
	 * then the config file, then check for a platform-specific default.
	 */
	private static ProgramPathDisabler createProgramPathsChain(Configuration configuration) {
		return  new ProgramPathDisabler(
				new WindowsRegistryProgramPaths(
				new ConfigurationProgramPaths(configuration, 
				new PlatformSpecificDefaultPathsFactory().get())));
	}

	public File getTempFolder() throws IOException {
		return tempFolder.getTempFolder();
	}

	public String getVlcPath() {
		return programPaths.getVlcPath();
	}

	public void disableVlc() {
		programPaths.disableVlc();
	}

	public String getEac3toPath() {
		return programPaths.getEac3toPath();
	}

	public String getMencoderPath() {
		return programPaths.getMencoderPath();
	}

	public void disableMEncoder() {
		programPaths.disableMencoder();
	}

	public String getFfmpegPath() {
		return programPaths.getFfmpegPath();
	}

	public void disableFfmpeg() {
		programPaths.disableFfmpeg();
	}

	public String getMplayerPath() {
		return programPaths.getMplayerPath();
	}

	public void disableMplayer() {
		programPaths.disableMplayer();
	}

	public String getTsmuxerPath() {
		return programPaths.getTsmuxerPath();
	}

	public String getFlacPath() {
		return programPaths.getFlacPath();
	}
	
}