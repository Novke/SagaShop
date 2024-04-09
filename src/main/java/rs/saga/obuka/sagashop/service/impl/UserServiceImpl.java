package rs.saga.obuka.sagashop.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.saga.obuka.sagashop.dao.RoleDAO;
import rs.saga.obuka.sagashop.dao.UserDAO;
import rs.saga.obuka.sagashop.domain.PayPalAccount;
import rs.saga.obuka.sagashop.domain.Role;
import rs.saga.obuka.sagashop.domain.RoleName;
import rs.saga.obuka.sagashop.domain.User;
import rs.saga.obuka.sagashop.dto.user.CreateUserCmd;
import rs.saga.obuka.sagashop.dto.user.UpdateUserCmd;
import rs.saga.obuka.sagashop.dto.user.UserInfo;
import rs.saga.obuka.sagashop.dto.user.UserResult;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ErrorCode;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.mapper.UserMapper;
import rs.saga.obuka.sagashop.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;


    @Override
    public User save(CreateUserCmd cmd) throws ServiceException {
        User user = UserMapper.INSTANCE.createUserCmdToUser(cmd);
//        user.setRoles(List.of(new Role(RoleName.USER)));
        user.setRoles(roleDAO.findAll().stream().filter(r -> r.getName().equals(RoleName.USER)).collect(Collectors.toList()));

        try {
            user = userDAO.save(user);
        } catch (Exception e) {
            LOGGER.error(null, e);
            throw new ServiceException(ErrorCode.ERR_GEN_001, "Saving of user failed!", e);
        }
        return user;
    }

    @Override
    public List<UserResult> findAll() {
        return UserMapper.INSTANCE.listUserToListUserResult(userDAO.findAll());
    }

    @Override
    public UserInfo findById(Long id) {
        return UserMapper.INSTANCE.userToUserInfo(userDAO.findOne(id));
    }

    @Override
    public void update(UpdateUserCmd cmd) throws ServiceException {
        User user;
        try {
            user = userDAO.findOne(cmd.getId());
            if (user == null) {
                throw new ServiceException(ErrorCode.ERR_GEN_002, "User not found!");
            }
            UserMapper.INSTANCE.updateUserCmdToUser(user, cmd);
            userDAO.merge(user);
        } catch (DAOException e) {
            LOGGER.error(null, e);
            throw new ServiceException(ErrorCode.ERR_GEN_001, "Updating of user failed!", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        User user;
        user = userDAO.findOne(id);
        if (user != null){
            try {
                userDAO.delete(user);
            } catch (DAOException e) {
                throw new ServiceException("Deleting of user failed!", e);
            }
        } else {
            throw new ServiceException(ErrorCode.ERR_GEN_002, "User not found!");
        }
    }

    @Override
    public User linkPayPalAccount(Long id, PayPalAccount pp) throws DAOException {
        User user = userDAO.findOne(id);
        user.setPayPalAccount(pp);
        return userDAO.merge(user);
    }

    @Override
    public User addRole(Long userId, RoleName roleName) throws DAOException, ServiceException {
        User user = userDAO.findOne(userId);
        Optional<Role> optionalRole = roleDAO.findAll().stream().filter(r -> r.getName().equals(RoleName.USER)).findFirst();
        if (optionalRole.isEmpty()) throw new ServiceException(ErrorCode.ERR_GEN_002);
        Role role = optionalRole.get();

        user.getRoles().add(role);

        return userDAO.merge(user);
    }
}
