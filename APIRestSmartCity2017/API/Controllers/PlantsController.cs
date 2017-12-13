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
    [Route("api/Plants")]
    public class PlantsController : Controller
    {
        private readonly Jardin_BDContext _context;

        public PlantsController(Jardin_BDContext context)
        {
            _context = context;
        }

        // GET: api/Plants
        [HttpGet]
        public IEnumerable<Plant> GetPlant()
        {
            return _context.Plant;
        }

        // GET: api/Plants/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetPlant([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var plant = await _context.Plant.SingleOrDefaultAsync(m => m.Id == id);

            if (plant == null)
            {
                return NotFound();
            }

            return Ok(plant);
        }

        // PUT: api/Plants/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutPlant([FromRoute] decimal id, [FromBody] Plant plant)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != plant.Id)
            {
                return BadRequest();
            }

            _context.Entry(plant).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PlantExists(id))
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

        // POST: api/Plants
        [HttpPost]
        public async Task<IActionResult> PostPlant([FromBody] Plant plant)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Plant.Add(plant);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPlant", new { id = plant.Id }, plant);
        }

        // DELETE: api/Plants/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeletePlant([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var plant = await _context.Plant.SingleOrDefaultAsync(m => m.Id == id);
            if (plant == null)
            {
                return NotFound();
            }

            _context.Plant.Remove(plant);
            await _context.SaveChangesAsync();

            return Ok(plant);
        }

        private bool PlantExists(decimal id)
        {
            return _context.Plant.Any(e => e.Id == id);
        }
    }
}