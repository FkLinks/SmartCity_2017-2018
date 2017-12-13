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
    [Route("api/GuidedTours")]
    public class GuidedToursController : Controller
    {
        private readonly Jardin_BDContext _context;

        public GuidedToursController(Jardin_BDContext context)
        {
            _context = context;
        }

        // GET: api/GuidedTours
        [HttpGet]
        public IEnumerable<GuidedTour> GetGuidedTour()
        {
            return _context.GuidedTour;
        }

        // GET: api/GuidedTours/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetGuidedTour([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var guidedTour = await _context.GuidedTour.SingleOrDefaultAsync(m => m.Id == id);

            if (guidedTour == null)
            {
                return NotFound();
            }

            return Ok(guidedTour);
        }

        // PUT: api/GuidedTours/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutGuidedTour([FromRoute] decimal id, [FromBody] GuidedTour guidedTour)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != guidedTour.Id)
            {
                return BadRequest();
            }

            _context.Entry(guidedTour).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!GuidedTourExists(id))
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

        // POST: api/GuidedTours
        [HttpPost]
        public async Task<IActionResult> PostGuidedTour([FromBody] GuidedTour guidedTour)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.GuidedTour.Add(guidedTour);
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (GuidedTourExists(guidedTour.Id))
                {
                    return new StatusCodeResult(StatusCodes.Status409Conflict);
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtAction("GetGuidedTour", new { id = guidedTour.Id }, guidedTour);
        }

        // DELETE: api/GuidedTours/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteGuidedTour([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var guidedTour = await _context.GuidedTour.SingleOrDefaultAsync(m => m.Id == id);
            if (guidedTour == null)
            {
                return NotFound();
            }

            _context.GuidedTour.Remove(guidedTour);
            await _context.SaveChangesAsync();

            return Ok(guidedTour);
        }

        private bool GuidedTourExists(decimal id)
        {
            return _context.GuidedTour.Any(e => e.Id == id);
        }
    }
}