jQuery(document).ready(function($){

	/*
	 $("#MyButton").bind("click", function() {
		  fHardCodedFunction.apply(this, [someValue]);
	 });
	 */
	//$("#cart-item-count").bind("click", updateCartItemCount);
	updateCartItemCount();
});

	function updateCartItemCount()
	{
		$.ajax ({ 
	        url: '/cart/items/count', 
	        type: "GET", 
	        dataType: "json",
	        contentType: "application/json",
	        complete: function(responseData, status, xhttp){ 
	        	$('#cart-item-count').text('('+responseData.responseJSON.count+')');
	        }
	    });
	}

	function addItemToCart(sku)
	{
		$.ajax ({ 
	        url: '/cart/items', 
	        type: "POST", 
	        dataType: "json",
	        contentType: "application/json",
	        data : '{"sku":"'+ sku +'"}"',
	        complete: function(responseData, status, xhttp){
	        	updateCartItemCount();
	        	/*
	        	$.bootstrapGrowl("Item added to cart", 
	        					{ type: 'info',
	        						offset: {
						    			from: "top",
						    			amount: 50
						    		}
	        					}
	        	);
	        	*/
	        }
	    }); 
	}

	function updateCartItemQuantity(sku, quantity)
	{
		$.ajax ({ 
	        url: '/cart/items', 
	        type: "PUT", 
	        dataType: "json",
	        contentType: "application/json",
	        data : '{ "product" :{ "sku":"'+ sku +'"},"quantity":"'+quantity+'"}',
	        complete: function(responseData, status, xhttp){ 
	        	updateCartItemCount();        	
	        	location.href = '/cart' 
	        }
	    });
	}

	function removeItemFromCart(sku)
	{
		$.ajax ({ 
	        url: '/cart/items/'+sku, 
	        type: "DELETE", 
	        dataType: "json",
	        contentType: "application/json",
	        complete: function(responseData, status, xhttp){ 
	        	updateCartItemCount();
	        	location.href = '/cart' 
	        }
	    });
	}

