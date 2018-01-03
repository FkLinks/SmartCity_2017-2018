using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using APIRestSmartCity2017.Model;

namespace APIRestSmartCity2017.Controllers
{
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
        [Route("api/[controller]")]
        public class ValuesController : BaseController
        {
            public ValuesController(UserManager<ApplicationUser> userManager)
                : base(userManager)
            {

            }
            // GET api/values
            [HttpGet]
            public async Task<IEnumerable<string>> Get()
            {
                ApplicationUser monUtilisateur = await GetCurrentUserAsync();
                Console.WriteLine("L'utilisateur " + monUtilisateur.UserName + " vient d'appeler la méthode Get sur le Values Controller!!!");
                return new string[] { "value1", "value2" };
            }
        }
    }
