package rs.saga.obuka.sagashop.service;

import rs.saga.obuka.sagashop.domain.PayPalAccount;
import rs.saga.obuka.sagashop.domain.RoleName;
import rs.saga.obuka.sagashop.domain.User;
import rs.saga.obuka.sagashop.dto.user.CreateUserCmd;
import rs.saga.obuka.sagashop.dto.user.UpdateUserCmd;
import rs.saga.obuka.sagashop.dto.user.UserInfo;
import rs.saga.obuka.sagashop.dto.user.UserResult;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ServiceException;

import java.util.List;

public interface UserService {

    User save(CreateUserCmd cmd) throws ServiceException;

    List<UserResult> findAll();

    UserInfo findById(Long id);

    void update(UpdateUserCmd UserDTO) throws ServiceException;

    void delete(Long id) throws ServiceException;

    User linkPayPalAccount(Long id, PayPalAccount pp) throws DAOException;

    User addRole(Long userId, RoleName role) throws DAOException, ServiceException;
}
