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
    [Route("api/Chatrooms")]
    public class ChatroomsController : Controller
    {
        private readonly Jardin_BDContext _context;

        public ChatroomsController(Jardin_BDContext context)
        {
            _context = context;
        }

        // GET: api/Chatrooms
        [HttpGet]
        public IEnumerable<Chatroom> GetChatroom()
        {
            return _context.Chatroom;
        }

        // GET: api/Chatrooms/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetChatroom([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var chatroom = await _context.Chatroom.SingleOrDefaultAsync(m => m.UserChat == id);

            if (chatroom == null)
            {
                return NotFound();
            }

            return Ok(chatroom);
        }

        // PUT: api/Chatrooms/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutChatroom([FromRoute] string id, [FromBody] Chatroom chatroom)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != chatroom.UserChat)
            {
                return BadRequest();
            }

            _context.Entry(chatroom).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ChatroomExists(id))
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

        // POST: api/Chatrooms
        [HttpPost]
        public async Task<IActionResult> PostChatroom([FromBody] Chatroom chatroom)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Chatroom.Add(chatroom);
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (ChatroomExists(chatroom.UserChat))
                {
                    return new StatusCodeResult(StatusCodes.Status409Conflict);
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtAction("GetChatroom", new { id = chatroom.UserChat }, chatroom);
        }

        // DELETE: api/Chatrooms/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteChatroom([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var chatroom = await _context.Chatroom.SingleOrDefaultAsync(m => m.UserChat == id);
            if (chatroom == null)
            {
                return NotFound();
            }

            _context.Chatroom.Remove(chatroom);
            await _context.SaveChangesAsync();

            return Ok(chatroom);
        }

        private bool ChatroomExists(string id)
        {
            return _context.Chatroom.Any(e => e.UserChat == id);
        }
    }
}