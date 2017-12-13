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

namespace APIRestSmartCity2017.Controllers
{
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

        // GET: api/ApplicationUsers/5
        [HttpGet("{username}")]
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
            var newUser = new ApplicationUser
            {
                Birthdate = dto.Birthdate,
                Sex = dto.Sex,
                GeographicOrigins = dto.GeographicOrigins
            };

            IdentityResult result = await _userManager.CreateAsync(newUser, dto.Password);
            if(result.Succeeded)
            {
                ApplicationUser current = await _userManager.FindByNameAsync(dto.UserName);
                result = await _userManager.AddToRoleAsync(current, "User");
            }

            IdentityResult roleResult = await _userManager.AddToRoleAsync(newUser, "User");
            // TODO: retourner un Created à la place du Ok;
            return (result.Succeeded) ? Ok() : (IActionResult)BadRequest();
        }

        [HttpPost("Admin")]
        public async Task<IActionResult> Admin([FromBody] NewUserDTO dto)
        {
            if(IsInRole("Admin"))
            {
                return Unauthorized();
            }
            else
            {
                return Ok();
            }
        }

       [HttpDelete("{username}")]
        public async Task<IActionResult> DeleteUsers([FromRoute] String username)
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

    }
}