package Resources;

import java.util.List;

public class Content {
	private List<String> meshFileFormats;

	private MeshLoader meshLoader = new MeshLoader();

	public Asset load(String path) {
		Asset result = null;

		String extension = getExtension(path);
		if (extension != null) {
			if (meshFileFormats.contains(getExtension(path))) {
				result = meshLoader.loadAsset(path);
			}
		}

		return result;
	}

	private String getExtension(String path) {
		if (path.lastIndexOf(".") > -1)
			return path.substring(path.lastIndexOf("."));

		return null;
	}
}
