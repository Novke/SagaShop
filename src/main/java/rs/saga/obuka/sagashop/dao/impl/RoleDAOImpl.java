package rs.saga.obuka.sagashop.dao.impl;

import org.springframework.stereotype.Repository;
import rs.saga.obuka.sagashop.dao.RoleDAO;
import rs.saga.obuka.sagashop.domain.Role;

@Repository
public class RoleDAOImpl extends AbstractDAOImpl<Role, Long> implements RoleDAO {
}
