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
    [Route("api/Responsibles")]
    public class ResponsiblesController : Controller
    {
        private readonly Jardin_BDContext _context;

        public ResponsiblesController(Jardin_BDContext context)
        {
            _context = context;
        }

        // GET: api/Responsibles
        [HttpGet]
        public IEnumerable<Responsible> GetResponsible()
        {
            return _context.Responsible;
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

        // POST: api/Responsibles
        [HttpPost]
        public async Task<IActionResult> PostResponsible([FromBody] Responsible responsible)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Responsible.Add(responsible);
            try
            {
                await _context.SaveChangesAsync();
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

        // DELETE: api/Responsibles/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteResponsible([FromRoute] decimal id)
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

        private bool ResponsibleExists(decimal id)
        {
            return _context.Responsible.Any(e => e.RegistrationNumber == id);
        }
    }
}