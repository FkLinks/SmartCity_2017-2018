using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIRestSmartCity2017.Model
{
    public partial class ApplicationUser : IdentityUser
    {
        public DateTime Birthdate { get; set; }     
        public string Sex { get; set; }
        public string GeographicOrigins { get; set; }

        public ApplicationUser() : base() { }

        public ICollection<Booking> Bookings { get; set; }
    }
}
