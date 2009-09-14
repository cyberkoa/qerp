var Confirm = Class.create();
Confirm.prototype = {
        initialize: function(element, message) {
                this.message = message;
                Event.observe($(element), 'click', this.doConfirm.bindAsEventListener(this));
        },
        
        doConfirm: function(e) {
                if(! confirm(this.message))
                        e.stop();
        }
}


var ConfirmNull = Class.create();
ConfirmNull.prototype = {
        initialize: function(element, message) {
                this.message = message;
                Event.observe($(element), 'click', this.doConfirm.bindAsEventListener(this));
        },
        
        doConfirm: function(e) {
                alert(this.message);
                        e.stop();
        }
}
