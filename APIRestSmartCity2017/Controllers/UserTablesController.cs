using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using APIRestSmartCity2017;
using APIRestSmartCity2017.Model;

namespace APIRestSmartCity2017.Controllers
{
    [Produces("application/json")]
    [Route("api/UserTables")]
    public class ApplicationUserController : Controller
    {
        private readonly Jardin_BDContext _context;

        public ApplicationUserController(Jardin_BDContext context)
        {
            _context = context;
        }

        // GET: api/UserTables
        [HttpGet]
        public IEnumerable<ApplicationUser> GetUserTable()
        {
            return _context.ApplicationUser;
        }

        // GET: api/UserTables/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetUserTable([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var userTable = await _context.ApplicationUser.SingleOrDefaultAsync(m => m.UserName == id);

            if (userTable == null)
            {
                return NotFound();
            }

            return Ok(userTable);
        }

        // PUT: api/UserTables/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUserTable([FromRoute] string id, [FromBody] ApplicationUser userTable)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != userTable.UserName)
            {
                return BadRequest();
            }

            _context.Entry(userTable).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserTableExists(id))
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

        // POST: api/UserTables
        [HttpPost]
        public async Task<IActionResult> PostUserTable([FromBody] ApplicationUser userTable)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.ApplicationUser.Add(userTable);
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (UserTableExists(userTable.UserName))
                {
                    return new StatusCodeResult(StatusCodes.Status409Conflict);
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtAction("GetUserTable", new { id = userTable.UserName }, userTable);
        }

        // DELETE: api/UserTables/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUserTable([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var userTable = await _context.ApplicationUser.SingleOrDefaultAsync(m => m.UserName == id);
            if (userTable == null)
            {
                return NotFound();
            }

            _context.ApplicationUser.Remove(userTable);
            await _context.SaveChangesAsync();

            return Ok(userTable);
        }

        private bool UserTableExists(string id)
        {
            return _context.ApplicationUser.Any(e => e.UserName == id);
        }
    }
}