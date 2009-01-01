var DynamicExtensions = {
    dynamicize: function(element){
        element.dHref = element.href;
		element.dTarget = element.target;
		element.href = "#"+element.target;
		element.target = "";
		element.dynamic = dynamicallyLoad.bindAsEventListener(element);
		Event.observe(element, 'click', element.dynamic);
		return element;
    }
}

Element.addMethods(DynamicExtensions);

function dynamicallyLoad(e) {
	element = Event.element(e);
	new Ajax.Updater(element.dTarget, element.dHref, { 
		method: 'get', 
		onComplete: function() { 
			setLinks(element,element.dTarget);
			new Effect.Highlight(element.dTarget, {duration: 1});
		 } 
	});
}


function setLinks(e, target) {
	(target) ? selector = '#'+target+' ' : selector = '';
	$$(selector+'a.dynamic').invoke('dynamicize');
}

Event.observe(window, 'load', setLinks);


function toggleRows(elm) {
 var rows = document.getElementsByTagName("TR");
 elm.className = "folderclose"; 
 var newDisplay = "none";
 var thisID = elm.parentNode.parentNode.parentNode.id + "-";
 // Are we expanding or contracting? If the first child is hidden, we expand
  for (var i = 0; i < rows.length; i++) {
   var r = rows[i];
   if (matchStart(r.id, thisID, true)) {
    if (r.style.display == "none") {
     if (document.all) newDisplay = "block"; //IE4+ specific code
     else newDisplay = "table-row"; //Netscape and Mozilla
	 elm.className = "folderopen"; 
    }
    break;
   }
 }
 
 // When expanding, only expand one level.  Collapse all desendants.
 var matchDirectChildrenOnly = (newDisplay != "none");

 for (var j = 0; j < rows.length; j++) {
   var s = rows[j];
   if (matchStart(s.id, thisID, matchDirectChildrenOnly)) {
     s.style.display = newDisplay;
     var cell = s.getElementsByTagName("TD")[0];
     var tier = cell.getElementsByTagName("DIV")[0];
     var folder = tier.getElementsByTagName("A")[0];
     if (folder.getAttribute("onclick") != null) {
	  folder.className = "folderclose"; 
     }
   }
 }
}

function toggleRowsDiv(elm) {
	var folder = elm.getElementsByTagName("A")[0];
	toggleRows(folder);	
}

function matchStart(target, pattern, matchDirectChildrenOnly) {
 var pos = target.indexOf(pattern);
 if (pos != 0) return false;
 if (!matchDirectChildrenOnly) return true;
 if (target.slice(pos + pattern.length, target.length).indexOf("-") >= 0) return false;
 return true;
}

function collapseAllRows() {
 var rows = document.getElementsByTagName("TR");
 for (var j = 0; j < rows.length; j++) {
   var r = rows[j];
   if (r.id.indexOf("-") >= 0) {
     r.style.display = "none";    
   }
 }
}
function toggle(div_id) {
		var el = document.getElementById(div_id);
		if ( el.style.display == 'none' ) {	el.style.display = 'block';}
		else {el.style.display = 'none';}
	}
	function blanket_size(popUpDivVar) {
		if (typeof window.innerWidth != 'undefined') {
			viewportheight = window.innerHeight;
		} else {
			viewportheight = document.documentElement.clientHeight;
		}
		if ((viewportheight > document.body.parentNode.scrollHeight) && (viewportheight > document.body.parentNode.clientHeight)) {
			blanket_height = viewportheight;
		} else {
			if (document.body.parentNode.clientHeight > document.body.parentNode.scrollHeight) {
				blanket_height = document.body.parentNode.clientHeight;
			} else {
				blanket_height = document.body.parentNode.scrollHeight;
			}
		}
		var blanket = document.getElementById('blanket');
		blanket.style.height = blanket_height + 'px';
		var popUpDiv = document.getElementById(popUpDivVar);
		popUpDiv_height=blanket_height/2-150;//150 is half popup's height
		popUpDiv.style.top = popUpDiv_height + 'px';
	}
	function window_pos(popUpDivVar) {
		if (typeof window.innerWidth != 'undefined') {
			viewportwidth = window.innerHeight;
		} else {
			viewportwidth = document.documentElement.clientHeight;
		}
		if ((viewportwidth > document.body.parentNode.scrollWidth) && (viewportwidth > document.body.parentNode.clientWidth)) {
			window_width = viewportwidth;
		} else {
			if (document.body.parentNode.clientWidth > document.body.parentNode.scrollWidth) {
				window_width = document.body.parentNode.clientWidth;
			} else {
				window_width = document.body.parentNode.scrollWidth;
			}
		}
		var popUpDiv = document.getElementById(popUpDivVar);
		window_width=window_width/2-150;//150 is half popup's width
		popUpDiv.style.left = window_width + 'px';
	}
	function popup(windowname) {
		blanket_size(windowname);
		window_pos(windowname);
		toggle('blanket');
		toggle(windowname);		
	}