using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using APIRestSmartCity2017.DTO;
using APIRestSmartCity2017.Model;
using Microsoft.AspNetCore.Authorization;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authentication.JwtBearer;

namespace APIRestSmartCity2017.Controllers
{
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/[controller]")]
    public class AccountController : BaseController
    {
        private UserManager<ApplicationUser> _userManager;
        private readonly Jardin_BDContext _context;

        public AccountController(UserManager<ApplicationUser> userManager, Jardin_BDContext _context) :base(userManager)
        {
            this._userManager = userManager;
            this._context = _context;
        }

                                     
        [HttpGet]
        public IEnumerable<ApplicationUser> GetApplicationUsers()
        {
            return _context.ApplicationUser.ToList();
        }

        [HttpGet]
        [Route("Count/Users")]
        public async Task<int> CountApplicationUsersAsync()
        {
            IList<String> role = await GetUsersRoles();
            if (role.Contains("Admin"))
            {
                return _context.ApplicationUser.Count();
            }
            return 0;
        }

        // GET: api/ApplicationUsers/5
        [HttpGet("{username}")]
        [AllowAnonymous]
        public async Task<IActionResult> GetApplicationUser([FromRoute] string username)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var user = await _context.ApplicationUser.SingleOrDefaultAsync(m => m.UserName == username);

            if (user == null)
            {
                return NotFound();
            }

            return Ok(user);

        }

        [AllowAnonymous]
        [HttpPost]
        public async Task<IActionResult> Post([FromBody]NewUserDTO dto)
        {
            using (var transaction = _context.Database.BeginTransaction())
            {
                IdentityResult result = null;

                try
                {
                    var newUser = new ApplicationUser
                    {
                        UserName = dto.UserName,
                        Birthdate = dto.Birthdate,
                        Email = dto.Email,
                        Sex = dto.Sex,
                        GeographicOrigins = dto.GeographicOrigins
                    };

                    result = await _userManager.CreateAsync(newUser, dto.Password);
                    if (result.Succeeded)
                    {
                        ApplicationUser current = await _userManager.FindByNameAsync(dto.UserName);
                        result = await _userManager.AddToRoleAsync(current, "User");
                    }

                    _context.SaveChanges();
                    transaction.Commit();
                    
                    return (result.Succeeded) ? Ok() : (IActionResult)BadRequest();       
                }
                catch(Exception)
                {
                    return BadRequest(result.Errors);
                }
            }
        }

        [HttpDelete("{username}")]
        public async Task<IActionResult> DeleteUsers([FromRoute] String username)
        {
            IList<String> role = await GetUsersRoles();
            if (role.Contains("Admin"))
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest(ModelState);
                }

                var uti = await _context.ApplicationUser.SingleOrDefaultAsync(m => m.UserName == username);
                if (uti == null)
                {
                    return NotFound();
                }

                _context.ApplicationUser.Remove(uti);
                await _context.SaveChangesAsync();

                return Ok(uti);
            }
            return Unauthorized();
        }

        [HttpGet]
        [Route("Roles")]
        public async Task<IActionResult> GetUserRoleAsync()
        {
            IList<String> roles = await GetUsersRoles();
            if(roles.Contains("Admin"))
            {
                return Ok();
            }
            return Unauthorized();
        }

    }
}