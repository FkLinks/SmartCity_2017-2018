using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using APIRestSmartCity2017.DTO;
using APIRestSmartCity2017.Model;
using Microsoft.AspNetCore.Authorization;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authentication.JwtBearer;

namespace APIRestSmartCity2017.Controllers
{
    //[Produces("application/json")]
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/Gardens")]
    public class GardensController : BaseController
    {
        private UserManager<ApplicationUser> _userManager;
        private readonly Jardin_BDContext _context;

        public GardensController(UserManager<ApplicationUser> userManager, Jardin_BDContext _context) :base(userManager)
        {
            this._userManager = userManager;
            this._context = _context;
        }

        // GET: api/Gardens
        [AllowAnonymous]
        [HttpGet]
        public IEnumerable<Garden> GetGarden()
        {
            return _context.Garden;
        }

        [HttpGet]
        [Route("Count/Gardens")]
        public async Task<int> CountGardensAsync()
        {
            IList<String> role = await GetUsersRoles();
            if (role.Contains("Admin"))
            {
                return _context.Garden.Count();
            }
            return 0;
        }

        // GET: api/Gardens/5
        [HttpGet("{id}")]
        [AllowAnonymous]
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
            IList<String> role = await GetUsersRoles();
            if (role.Contains("Admin"))
            {
                using (var transaction = _context.Database.BeginTransaction())
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
                        transaction.Commit();
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
            }
            return Unauthorized();
        }

        // POST: api/Gardens
        [HttpPost]
        public async Task<IActionResult> PostGarden([FromBody] GardenDTO garden)
        {
            IList<String> role = await GetUsersRoles();
            if (role.Contains("Admin"))
            {
                using (var transaction = _context.Database.BeginTransaction())
                {
                    try
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
                            UrlAudio = garden.urlAudio,
                            UrlImg = garden.UrlImg,
                            Note = garden.Note
                        };

                        _context.Garden.Add(newGarden);
                        await _context.SaveChangesAsync();
                        transaction.Commit();

                        return CreatedAtAction("GetGarden", new { id = garden.NumGarden }, garden);
                    }
                    catch (Exception)
                    {
                        return BadRequest();
                    }
                }
            }
            return Unauthorized();
            
        }

        // DELETE: api/Gardens/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteGarden([FromRoute] decimal id)
        {
            IList<String> role = await GetUsersRoles();
            if (role.Contains("Admin"))
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
            return Unauthorized();
        }

        private bool GardenExists(decimal id)
        {
            return _context.Garden.Any(e => e.NumGarden == id);
        }
    }
}