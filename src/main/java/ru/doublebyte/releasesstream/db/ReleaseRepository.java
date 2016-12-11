package ru.doublebyte.releasesstream.db;

import org.springframework.data.repository.CrudRepository;

/**
 * JPA Releases repository
 */
public interface ReleaseRepository extends CrudRepository<Release, String> {
}
