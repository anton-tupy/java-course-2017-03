package com.jcourse.kladov;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
					width = Integer.valueOf(src.attr("width"));
					height = Integer.valueOf(src.attr("height"));
				} catch (NumberFormatException e) {
				}
				result.add(new ImageDescriptor(src.attr("abs:src"), width, height));
			}
		}

		return result;
	}
}
