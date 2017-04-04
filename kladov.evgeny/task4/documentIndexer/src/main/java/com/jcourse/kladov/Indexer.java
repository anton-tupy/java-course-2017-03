package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

@Log4j
public class Indexer {
	Vector<Metric> metrics = new Vector<>();

	public Indexer(Metric[] metrics) {
		Collections.addAll(this.metrics, metrics);
	}

	public void processDocument(Document doc) {
		metrics.forEach((m) -> {
			try {
				m.countOn(doc);
			} catch (IOException e) {
				log.warn("Reading problem with " + doc, e);
			}
		});
	}

	public void serializeMetrics(MetricSerializer serializer) {
		metrics.forEach((m) -> serializer.serialize(m));
	}
}
