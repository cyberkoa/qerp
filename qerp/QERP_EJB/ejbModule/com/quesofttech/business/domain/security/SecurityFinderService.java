package com.quesofttech.business.domain.security;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.lang.StringBuilder;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.quesofttech.business.domain.security.dto.UserSearchFields;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceLocal;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;

import com.quesofttech.business.common.exception.AuthenticationException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.query.ComparisonOperator;
import com.quesofttech.business.common.query.QueryBuilder;
import com.quesofttech.business.common.query.SearchOptions;
import com.quesofttech.business.domain.base.BaseService;


import java.io.FileWriter;
import java.io.BufferedWriter;


@Stateless
@Local(ISecurityFinderServiceLocal.class)
@Remote(ISecurityFinderServiceRemote.class)
public class SecurityFinderService extends BaseService implements ISecurityFinderServiceLocal, ISecurityFinderServiceRemote {

	private void outputToFile(String s)
	{
	      try{
	    	    // Create file 
	    	    FileWriter fstream = new FileWriter("out.txt");
	    	        BufferedWriter out = new BufferedWriter(fstream);
	    	    out.append(s);
	    	    //Close the output stream
	    	    out.close();
	    	    }catch (Exception e){//Catch exception if any
	    	      System.err.println("Error: " + e.getMessage());
	    	    }
	}
	
	
	public User findUser(Long id) throws DoesNotExistException {
		User obj = (User) find(User.class, id);
		return obj;
	}
	
	public UserPassword findUserPassword(Long id) {
		UserPassword obj=null;
		try
		{
			obj = (UserPassword) find(UserPassword.class, id);
		}
		catch (DoesNotExistException e)
		{
			
		}
		return obj;
	}

	public User authenticateUser(String login, String password) throws AuthenticationException {
		User user = null;
		try {
			user = findUserByLogin(login);
		}
		catch (DoesNotExistException e) {
			throw new AuthenticationException("User_loginId_unknown", login);
			
		}
		user.authenticate(password);
		return user;
	}

	public User findUserByLogin(String login) throws DoesNotExistException {

		try {
			Query q = _em.createQuery("select u from User u where u.rowInfo.recordStatus='A' AND u.login = :login");
			q.setParameter("login", login);
			User obj = (User) q.getSingleResult();
			return obj;
		}
		catch (NoResultException e) {
			throw new DoesNotExistException(User.class, login);
		}
		catch (NonUniqueResultException e) {
			throw new IllegalStateException("Duplicate User found with login = " + login + ".");
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> findUsers() {

		Query q = _em.createQuery("select u from User u where u.rowInfo.recordStatus='A' order by u.login");
		List l = q.getResultList();

		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<Module> findModules(){
		Query q = _em.createQuery("select m from Module where m.rowInfo.recordStatus='A'");
		List l = q.getResultList();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<User> findUsers(UserSearchFields search, SearchOptions options) {

		QueryBuilder builder = new QueryBuilder();
		builder.append("select u from User u");
		builder.appendEqualsSkipEmpty("u.recordStatus", search.getRecordStatus());
		builder.appendLikeIgnoreCaseSkipEmpty("u.login", search.getLogin());
		builder.appendEqualsSkipEmpty("u.salutation", search.getSalutation());
		builder.appendLikeIgnoreCaseSkipEmpty("u.firstName", search.getFirstName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.lastName", search.getLastName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.emailAddress", search.getEmailAddress());
		builder.appendComparison("u.expiryDate", ComparisonOperator.EQ, search.getExpiryDate());
		builder.appendEqualsSkipEmpty("u.version", search.getVersion());

		if (options.getSortColumnNames().size() == 0) {
			builder.append(" order by u.login");
		}

		Query q = builder.createQuery(_em, options, "u");

		List l = q.getResultList();

		return l;
	}

	
	@SuppressWarnings("unchecked")
	public List<Role> findUserRoles(User user)  throws DoesNotExistException  {

		
		Query q = _em.createQuery("select ur from UserRole ur" +
				                  " where" +
				                  " ur.rowInfo.recordStatus='A' AND ur.user.id = :userId" +
				                  " order by ur.role");
		q.setParameter("userId", user.getId());
		
		List<Role> l = (List<Role>)q.getResultList();
		
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> findUserRoles(String login)  throws DoesNotExistException  {

		Query q = _em.createQuery("select r from Role r" +
				                        " join r.userRoles ur" +
				                        " join ur.user u" +
				                        " where" +
				                        " r.rowInfo.recordStatus='A' AND" +
				                        " ur.rowInfo.recordStatus='A' AND" +
				                        " u.rowInfo.recordStatus='A' AND" +
				                        " u.login = :login" + 
				                        " order by r.role");
		q.setParameter("login", login);
		
		List<Role> l = (List<Role>) q.getResultList();
		
		return l;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Program> findAuthorizedProgramsOfUserByUser(User user)  throws DoesNotExistException {
		
		Query q = _em.createQuery("select distinct p from Program p" +
                                  " join p.userPrograms up" +
                                  " where" +
                                  " p.rowInfo.recordStatus='A' AND up.rowInfo.recordStatus='A'" +
                                  " AND up.user.id = :userId");
        		
		q.setParameter("userId", user.getId());

		List<Program> l = (List<Program>) q.getResultList();

		return l;
	}

	
	@SuppressWarnings("unchecked")
	public List<Program> findAuthorizedProgramsByRole(Role role)  throws DoesNotExistException  {
		
		
		Query q = _em.createQuery("select distinct p from Program p" +
				                  " join p.rolePrograms rp" +
				                  " where" +
				                  " p.rowInfo.recordStatus='A' AND" +
				                  " rp.rowInfo.recordStatus='A' AND" +
				                  " rp.role.id = :roleId");
				                       		
		q.setParameter("roleId", role.getId());
		
		List<Program> l = (List<Program>) q.getResultList();
		
		return l;
	}

	
/*	
	@SuppressWarnings("unchecked")
	public List<Program> findAuthorizedProgramsOfRolesByUser(User user)  throws DoesNotExistException  {
		
		Query q = _em.createQuery("select distinct p from Program p" +
                " join p.rolePrograms rp where rp.role in" +
                " (select ur.role from UserRole ur" +
                " join ur.user u where ur.user.id = :userId) ");
                		
		q.setParameter("userId", user.getId());

		List<Program> l = (List<Program>) q.getResultList();
		
		return l;
	}
*/
	
/*	
	@SuppressWarnings("unchecked")
	public List<Program> findAuthorizedProgramsOfRolesByUser(User user)  throws DoesNotExistException  {
		
		List<Role> roles = findUserRoles(user.getLogin());
		List<Program> programs = new ArrayList<Program> ();	
		
		for(Role r : roles)
		{
			
			Query q = _em.createQuery("select distinct p from Program p" +
	                " join p.rolePrograms rp where rp.role.id = :roleId");
	                		
			q.setParameter("roleId", r.getId());
			
			List<Program> p = (List<Program>) q.getResultList();
			
			programs.addAll(p);
		}
		
		
		// Remove duplicate programs in List
		List<Program> l = new ArrayList<Program> ();
		
		for(Program p : programs)
		{
			if(!l.contains(p))
			{
				l.add(p);
			}
		}
		
		return l;
	}
*/
	@SuppressWarnings("unchecked")
	public List<Program> findAuthorizedProgramsOfRolesByUser(User user)  throws DoesNotExistException  {
		
		List<Role> roles = findUserRoles(user.getLogin());
	
		outputToFile("[findAuthorizedProgramsOfRolesByUser] Count : " + roles.size() + "\n");
		
		//StringBuilder sb = new StringBuilder();
		List<Long> roleIds = new ArrayList<Long>(); 
		
		for(Role r : roles)
		{
			roleIds.add(r.getId());
		}	
		
		//outputToFile("[findAuthorizedProgramsOfRolesByUser] " + sb.toString() + "\n");
				
			Query q = _em.createQuery("select distinct p from Program p" +
	                " join p.rolePrograms rp" +
	                " join rp.role r" +
	                " where" +
	                " p.rowInfo.recordStatus='A' AND" +
	                " rp.rowInfo.recordStatus='A' AND" +
	                " r.rowInfo.recordStatus='A' AND" +
	                " r.id in (:roleIds)"
	                );
	                		
			q.setParameter("roleIds", roleIds);
			
			List<Program> programs = (List<Program>) q.getResultList();
			
			//programs.addAll(p);
		
		return programs;
		/*
		// Remove duplicate programs in List
		List<Program> l = new ArrayList<Program> ();
		
		for(Program p : programs)
		{
			if(!l.contains(p))
			{
				l.add(p);
			}
		}
		
		return l;
		*/
	}

	
	@SuppressWarnings("unchecked")
	public List<Program> findAuthorizedProgramsByUser(User user)  throws DoesNotExistException  {

		List<Program> programsOfUserRoles = findAuthorizedProgramsOfRolesByUser(user);
		
		List<Program> programsOfUser = findAuthorizedProgramsOfUserByUser(user);
		
		
		List<Program> programs = new ArrayList<Program>();
		programs.addAll(programsOfUserRoles);
		
       // Iterate the programsOfUser and add only those not in programs
		
		for(Program p : programsOfUser)
		{
			System.out.println("in Program p:");
			if(!(programs.contains(p)))
			{
				System.out.println("adding record program P");
				programs.add(p);
			}
		}
		
		return programs;
		
	}
		
	//@SuppressWarnings("unchecked")
	public List<Program> findAuthorizedProgramsByUserId(Long id)  throws DoesNotExistException  {
		User user = findUser(id);
	 
		return findAuthorizedProgramsByUser(user);
	}

	public Boolean IsAuthorizedToAccess(Long userId, String programCode)	throws DoesNotExistException{
		
		List<Program> programs = findAuthorizedProgramsByUserId(userId); 
		
		for(Program p : programs)
		{
			if(p.getCode().equalsIgnoreCase(programCode))
			{
				return true;
			}
		}
		
		return false;
	}
	
}
