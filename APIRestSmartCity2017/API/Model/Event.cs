using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class Event
    {
        public decimal Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime? EndTime { get; set; }
        public string Url { get; set; }

        public ICollection<Event_Garden> Event_Garden { get; set; }
    }
}
