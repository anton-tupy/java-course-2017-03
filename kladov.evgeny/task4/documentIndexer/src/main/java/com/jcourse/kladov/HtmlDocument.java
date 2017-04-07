package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class HtmlDocument implements Document {
	private org.jsoup.nodes.Document doc;

	public HtmlDocument(String url) throws IOException {
		doc = Jsoup.connect(url).get();
	}

	List<String> getLinks() {
		List<String> result = new ArrayList<>();
		doc.select("a[href]").forEach(link -> result.add(link.attr("abs:href")));
		return result;
	}

	@Override
	public WordTokenizer getWords() {
		return new WordTokenizer(new ByteArrayInputStream(doc.text().getBytes()));
	}

	@Override
	public List<ImageDescriptor> getImages() {
		List<ImageDescriptor> result = new ArrayList<>();
		Elements media = doc.select("[src]");

		for (Element src : media) {
			if (src.tagName().equals("img")) {
				int width = 0, height = 0;
				try {
					String strWidth = src.attr("width");
					if (strWidth.length() > 0)
						width = Integer.valueOf(strWidth);
					String strHeight = src.attr("height");
					if (strHeight.length() > 0)
						height = Integer.valueOf(strHeight);
				} catch (NumberFormatException e) {
					log.trace("Converting to int from string failed ", e);
				}
				result.add(new ImageDescriptor(src.attr("abs:src"), width, height));
			}
		}

		return result;
	}
}
