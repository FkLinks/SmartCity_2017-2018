using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace APIRestSmartCity2017.DTO
{
    public class NewUserDTO
    {
        [StringLength(100, MinimumLength = 3, ErrorMessage = "Username Should be minimum 3 characters")]
        public string UserName { get; set; }

        [StringLength(100, MinimumLength = 8, ErrorMessage = "Password Should be minimum 8 characters with digits, upper and lower case !")]
        public string Password { get; set; }

        public DateTime Birthdate { get; set; }

        [StringLength(100, MinimumLength = 5, ErrorMessage = "Email not valid")]
        public string Email { get; set; }

        [StringLength(100, MinimumLength = 1, ErrorMessage = "Sex not valid")]
        public string Sex { get; set; }

        public string GeographicOrigins { get; set; }
    }
}
