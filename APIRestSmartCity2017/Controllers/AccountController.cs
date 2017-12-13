using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using APIRestSmartCity2017.DTO;
using APIRestSmartCity2017.Model;
using Microsoft.AspNetCore.Authorization;

namespace APIRestSmartCity2017.Controllers
{
    [Route("api/[controller]")]
    public class AccountController : Controller
    {
        private UserManager<ApplicationUser> _userManager;
        public AccountController(UserManager<ApplicationUser> userManager)
        {
            this._userManager = userManager;
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody]NewUserDTO dto)
        {
            var newUser = new ApplicationUser
            {
                UserName = dto.UserName,
                Birthdate = dto.Birthdate,
                Email = dto.Email,                
                Sex = dto.Sex,
                GeographicOrigins = dto.GeographicOrigins
            };

            IdentityResult result = await _userManager.CreateAsync(newUser, dto.Password);
            // TODO: retourner un Created à la place du Ok;
            return (result.Succeeded) ? Ok() : (IActionResult)BadRequest();
        }
    }
}