package group.comm.hacktainan.application;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

final class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
	public static int getDefaultLruCacheSize() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 4096);
		final int cacheSize = maxMemory / 64;

		return cacheSize;
	}

	public BitmapLruCache() {
		this(getDefaultLruCacheSize());
	}

	public BitmapLruCache(int sizeInKiloBytes) {
		super(sizeInKiloBytes);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight() / 1024;
	}

	@Override
	public Bitmap getBitmap(String url) {
		return get(url);
	}

	@Override
	public void putBitmap(final String url, final Bitmap bitmap) {
		put(url, getResizedBitmap(bitmap,60));
	}

	private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
		int width = image.getWidth();
		int height = image.getHeight();

		float bitmapRatio = width / height;
		if (bitmapRatio > 0) {
			width = maxSize;
			height = (int) (width / bitmapRatio);
		} else {
			height = maxSize;
			width = (int) (height * bitmapRatio);
		}
		if(width<=0||height<=0)
			return Bitmap.createScaledBitmap(image, maxSize, maxSize, true);
		return Bitmap.createScaledBitmap(image, width, height, true);
	}

}