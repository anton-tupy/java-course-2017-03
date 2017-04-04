package com.jcourse.kladov;

import org.jsoup.Jsoup;

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
}
