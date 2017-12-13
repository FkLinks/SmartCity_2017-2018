using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class Booking
    {
        public decimal Price { get; set; }
        public string BookingState { get; set; }
        public string PayementState { get; set; }
        public string UserBooking { get; set; }
        public decimal GuidedTour { get; set; }

        public GuidedTour GuidedTourNavigation { get; set; }
        public ApplicationUser UserBookingNavigation { get; set; }
    }
}
