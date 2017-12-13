using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class Chatroom
    {
        public string Wording { get; set; }
        public DateTime SendDate { get; set; }
        public string UserChat { get; set; }
        public decimal Responsible { get; set; }

        public Responsible ResponsibleNavigation { get; set; }
        public ApplicationUser UserChatNavigation { get; set; }
    }
}
