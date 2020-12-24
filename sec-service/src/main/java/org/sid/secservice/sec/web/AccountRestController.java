package org.sid.secservice.sec.web;

import java.util.List;

import org.sid.secservice.sec.entities.AppRole;
import org.sid.secservice.sec.entities.AppUser;
import org.sid.secservice.sec.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
public class AccountRestController {

	private AccountService accountService;

	public AccountRestController(AccountService accountService) {
		
		this.accountService = accountService;
	}
	@GetMapping(path="users")
	public List<AppUser> AppUsers()
	{
	return accountService.listUsers();	
	}
	@PostMapping(path="users")
	public AppUser saveUser(@RequestBody AppUser appUser)
	{
		return accountService.addNewUser(appUser);
	}
	@PostMapping(path="roles")
	public AppRole saveRole(@RequestBody AppRole appRole)
	{
		return accountService.addNewRole(appRole);
	}
	@PostMapping(path="addRoleToUser")
	public void addRoleToUser(@RequestBody RoleUserForm roleUserForm)
	{
	 accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRolename());
	}
	
	
	}
@Data
class RoleUserForm{
	private String username;
	private String rolename;
	
}
