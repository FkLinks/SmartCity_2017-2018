using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using APIRestSmartCity2017;
using APIRestSmartCity2017.Model;
using APIRestSmartCity2017.DTO;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authentication.JwtBearer;

namespace APIRestSmartCity2017.Controllers
{
    [Produces("application/json")]
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/Responsibles")]
    public class ResponsiblesController : BaseController
    {
        private readonly Jardin_BDContext _context;
        private UserManager<ApplicationUser> _userManager;

        public ResponsiblesController(UserManager<ApplicationUser> userManager, Jardin_BDContext context) :base(userManager)
        {
            this._context = context;
            this._userManager = userManager;
        }

        // GET: api/Responsibles
        [HttpGet]
        public IEnumerable<Responsible> GetResponsibleAsync()
        {         
            return _context.Responsible;
        }

        [HttpGet]
        [Route("Count/Responsibles")]
        public async Task<int> CountResponsiblesAsync()
        {
            IList<String> role = await GetUsersRoles();
            if (role.Contains("Admin"))
            {
                return _context.Responsible.Count();
            }
            return 0;
        }

        // GET: api/Responsibles/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetResponsible([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var responsible = await _context.Responsible.SingleOrDefaultAsync(m => m.RegistrationNumber == id);

            if (responsible == null)
            {
                return NotFound();
            }

            return Ok(responsible);
        }

        // PUT: api/Responsibles/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutResponsible([FromRoute] decimal id, [FromBody] Responsible responsible)
        {
            IList<String> role = await GetUsersRoles();
            if (role.Contains("Admin"))
            {
                using (var transaction = _context.Database.BeginTransaction())
                {
                    if (!ModelState.IsValid)
                    {
                        return BadRequest(ModelState);
                    }

                    if (id != responsible.RegistrationNumber)
                    {
                        return BadRequest();
                    }

                    _context.Entry(responsible).State = EntityState.Modified;

                    try
                    {
                        await _context.SaveChangesAsync();
                        transaction.Commit();
                    }
                    catch (DbUpdateConcurrencyException)
                    {
                        if (!ResponsibleExists(id))
                        {
                            return NotFound();
                        }
                        else
                        {
                            throw;
                        }
                    }

                    return NoContent();
                }
            }
            return Unauthorized();
        }

        // POST: api/Responsibles
        [HttpPost]
        public async Task<IActionResult> PostResponsible([FromBody] ResponsibleDTO responsible)
        {
            IList<String> role = await GetUsersRoles();
            if (role.Contains("Admin"))
            {
                using (var transaction = _context.Database.BeginTransaction())
                {
                    if (!ModelState.IsValid)
                    {
                        return BadRequest(ModelState);
                    }

                    var newResponsible = new Responsible
                    {
                        RegistrationNumber = responsible.RegistrationNumber,
                        Login = responsible.Login,
                        Password = responsible.Password,
                        Age = responsible.Age,
                        Sex = responsible.Sex,
                        PhoneNumber = responsible.PhoneNumber
                    };

                    _context.Responsible.Add(newResponsible);
                    try
                    {
                        await _context.SaveChangesAsync();
                        transaction.Commit();
                    }
                    catch (DbUpdateException)
                    {
                        if (ResponsibleExists(responsible.RegistrationNumber))
                        {
                            return new StatusCodeResult(StatusCodes.Status409Conflict);
                        }
                        else
                        {
                            throw;
                        }
                    }

                    return CreatedAtAction("GetResponsible", new { id = responsible.RegistrationNumber }, responsible);
                }
            }
            return Unauthorized();
        }

        // DELETE: api/Responsibles/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteResponsible([FromRoute] decimal id)
        {
            IList<String> role = await GetUsersRoles();
            if (role.Contains("Admin"))
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest(ModelState);
                }

                var responsible = await _context.Responsible.SingleOrDefaultAsync(m => m.RegistrationNumber == id);
                if (responsible == null)
                {
                    return NotFound();
                }

                _context.Responsible.Remove(responsible);
                await _context.SaveChangesAsync();

                return Ok(responsible);
            }
            return Unauthorized();
        }

        private bool ResponsibleExists(decimal id)
        {
            return _context.Responsible.Any(e => e.RegistrationNumber == id);
        }
    }
}