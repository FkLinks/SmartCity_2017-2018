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
    [Route("api/Gardens")]
    public class GardensController : Controller
    {
        private readonly Jardin_BDContext _context;

        public GardensController(Jardin_BDContext context)
        {
            _context = context;
        }

        // GET: api/Gardens
        [HttpGet]
        public IEnumerable<Garden> GetGarden()
        {
            return _context.Garden;
        }

        // GET: api/Gardens/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetGarden([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var garden = await _context.Garden.SingleOrDefaultAsync(m => m.NumGarden == id);

            if (garden == null)
            {
                return NotFound();
            }

            return Ok(garden);
        }

        // PUT: api/Gardens/5        
        [HttpPut("{id}")]
        //[ExpectedException(typeof(DbUpdateConcurrencyException))]                                         //DEMANDER A MONSIEUR
        public async Task<IActionResult> PutGarden([FromRoute] decimal id, [FromBody] Garden garden)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != garden.NumGarden)
            {
                return BadRequest();
            }

            _context.Entry(garden).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!GardenExists(id))
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

        // POST: api/Gardens
        [HttpPost]
        public async Task<IActionResult> PostGarden([FromBody] Garden garden)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Garden.Add(garden);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetGarden", new { id = garden.NumGarden }, garden);
        }

        // DELETE: api/Gardens/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteGarden([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var garden = await _context.Garden.SingleOrDefaultAsync(m => m.NumGarden == id);
            if (garden == null)
            {
                return NotFound();
            }

            _context.Garden.Remove(garden);
            await _context.SaveChangesAsync();

            return Ok(garden);
        }

        private bool GardenExists(decimal id)
        {
            return _context.Garden.Any(e => e.NumGarden == id);
        }
    }
}