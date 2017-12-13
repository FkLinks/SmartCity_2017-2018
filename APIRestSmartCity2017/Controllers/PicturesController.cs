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
    [Route("api/Pictures")]
    public class PicturesController : Controller
    {
        private readonly Jardin_BDContext _context;

        public PicturesController(Jardin_BDContext context)
        {
            _context = context;
        }

        // GET: api/Pictures
        [HttpGet]
        public IEnumerable<Picture> GetPicture()
        {
            return _context.Picture;
        }

        // GET: api/Pictures/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetPicture([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var picture = await _context.Picture.SingleOrDefaultAsync(m => m.Id == id);

            if (picture == null)
            {
                return NotFound();
            }

            return Ok(picture);
        }

        // PUT: api/Pictures/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutPicture([FromRoute] decimal id, [FromBody] Picture picture)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != picture.Id)
            {
                return BadRequest();
            }

            _context.Entry(picture).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PictureExists(id))
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

        // POST: api/Pictures
        [HttpPost]
        public async Task<IActionResult> PostPicture([FromBody] Picture picture)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Picture.Add(picture);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPicture", new { id = picture.Id }, picture);
        }

        // DELETE: api/Pictures/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeletePicture([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var picture = await _context.Picture.SingleOrDefaultAsync(m => m.Id == id);
            if (picture == null)
            {
                return NotFound();
            }

            _context.Picture.Remove(picture);
            await _context.SaveChangesAsync();

            return Ok(picture);
        }

        private bool PictureExists(decimal id)
        {
            return _context.Picture.Any(e => e.Id == id);
        }
    }
}