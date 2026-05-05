
package com.wonho.sample_project.domains.store.repository;

import com.wonho.sample_project.domains.store.entity.Store;
import com.wonho.sample_project.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}

