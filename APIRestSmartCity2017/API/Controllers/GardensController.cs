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
        public async Task<IActionResult> PostGarden([FromBody] GardenDTO garden)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var newGarden = new Garden
            {
                Name = garden.Name,
                Superficie = garden.Superficie,
                Street = garden.Street,
                NumStreet = garden.NumStreet,
                Description = garden.Description,
                GeographicalCoordinate = garden.GeographicalCoordinate,
                //Picture = garden.Picture,
                Note = garden.Note
            };

            _context.Garden.Add(newGarden);
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