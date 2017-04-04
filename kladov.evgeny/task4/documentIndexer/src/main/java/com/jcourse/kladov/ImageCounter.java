package com.jcourse.kladov;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ImageCounter implements Metric {
	int totalImages = 0;
	int totalDocuments = 0;
	int totalWidth = 0;
	int totalHeight = 0;

	@Override
	public void countOn(Document doc) throws IOException {
		totalDocuments++;
		List<ImageDescriptor> images = doc.getImages();
		if (images == null)
			return;

		images.forEach(i-> {
			totalImages++;
			totalWidth += i.width;
			totalHeight += i.height;
		});
	}

	@Override
	public String getName() {
		return "ImageStats";
	}

	@Override
	public Iterator<Row> getIterator() {
		return new Iterator<Row>() {
			private boolean hasNext = true;

			@Override
			public boolean hasNext() {
				return hasNext;
			}

			@Override
			public Row next() {
				hasNext = false;

				return new Row( new Column[] {
						new Column(Integer.class, "Total images", totalImages),
						new Column(Integer.class, "Total documents", totalDocuments),
						new Column(Integer.class, "Images per document", totalDocuments == 0 ? 0 : totalImages / totalDocuments)
				});
			}
		};
	}
}
