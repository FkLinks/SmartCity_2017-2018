using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIRestSmartCity2017.DTO
{
    public class NewUserDTO
    {
        public string UserName { get; set; }
        public string Password { get; set; }
        public DateTime Birthdate { get; set; }
        public string Email { get; set; }
        public string Sex { get; set; }
        public string GeographicOrigins { get; set; }
        public string PhoneNumber { get; set; }

    }
}
