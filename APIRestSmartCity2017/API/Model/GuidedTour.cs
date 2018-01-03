using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class GuidedTour
    {
        public GuidedTour()
        {
            Booking = new HashSet<Booking>();
        }

        public decimal Id { get; set; }
        public decimal Price { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime? EndTime { get; set; }
        public decimal Garden { get; set; }
        public Garden Fk_Garden { get; set; }

        public ICollection<Booking> Booking { get; set; }
    }
}
