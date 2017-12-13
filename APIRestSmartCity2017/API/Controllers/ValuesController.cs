using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using APIRestSmartCity2017.Model;

namespace APIRestSmartCity2017.Controllers
{
    [Route("api/[controller]")]
    public class ValuesController : BaseController
    {
        public ValuesController(UserManager<ApplicationUser> userManager) : base(userManager)
        {

        }

        /*dotnet ef dbcontext scaffold "Server=jardin-bd.database.windows.net; Database=Jardin-BD; User Id=etu30759; Password=Ct519hOt;" Microsoft.EntityFrameworkCore.SqlServer --schema "dbo"*/

        // GET api/values
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody]string value)
        {
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
