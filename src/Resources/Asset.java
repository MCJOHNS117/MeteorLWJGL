package Resources;

public abstract class Asset {
	protected long assetId;

	protected Asset(long assetId) {
		this.assetId = assetId;
	}

	public long getAssetId() {
		return assetId;
	}

	protected abstract void dispose();
}