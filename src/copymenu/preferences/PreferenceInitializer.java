package copymenu.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import common.KeyValues;
import common.Utils;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {

		Utils.getPreferenceStore().setDefault(KeyValues.pathType, KeyValues.fullPath);
		Utils.getPreferenceStore().setDefault(KeyValues.popupFlag, false);
		
		Utils.getPreferenceStore().setDefault(KeyValues.homeDir, KeyValues._homeDir);
		Utils.getPreferenceStore().setDefault(KeyValues.warDir, KeyValues._warDir);
		Utils.getPreferenceStore().setDefault(KeyValues.treeDir, KeyValues._treeDir);
		Utils.getPreferenceStore().setDefault(KeyValues.webProjectDir, KeyValues._webProjectDir);
		Utils.getPreferenceStore().setDefault(KeyValues.projectName, KeyValues._projectName);
		Utils.getPreferenceStore().setDefault(KeyValues.inText, KeyValues._inText);
		
	}

}
