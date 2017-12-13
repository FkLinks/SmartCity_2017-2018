using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class Responsible
    {
        public Responsible()
        {
            Chatroom = new HashSet<Chatroom>();
        }

        public decimal RegistrationNumber { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public decimal Age { get; set; }
        public string Sex { get; set; }
        public string PhoneNumber { get; set; }

        public ICollection<Chatroom> Chatroom { get; set; }
    }
}
