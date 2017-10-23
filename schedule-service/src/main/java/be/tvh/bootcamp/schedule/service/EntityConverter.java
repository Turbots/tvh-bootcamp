package be.tvh.bootcamp.schedule.service;

@FunctionalInterface
public interface EntityConverter<SOURCE, TARGET> {

	TARGET convert(SOURCE source);
}
