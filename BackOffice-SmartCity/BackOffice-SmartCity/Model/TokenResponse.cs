using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BackOffice_SmartCity.Model
{
    public class TokenResponse
    {
        public string Access_Token { get; set; }
        public int Expires_in { get; set; }
    }
}
