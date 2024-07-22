// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
import "mx-global";

// BEGIN EXTRA CODE
function clickButton(buttonName) {
	//Get buttonElement. The mx-name is part of the class name

	const buttonElement = document.querySelector('button[class*="' + buttonName + '"]');
	if (buttonElement === null) {
		throw new Error("Button with that name could not be found.")
	}

	buttonElement.click();
}
// END EXTRA CODE

/**
 * This JavaScript action can be used in a nanoflow that was called from an event widget (when the component is loaded).
 * 
 * It is intended to create an event that listens to the user input in a textarea. Once either Enter or Shift+Enter is hitted, the specified button will be called. It is sufficient to call this action only once when the element is loaded.
 * 
 * Make sure that the names of the button and textarea match with the input parameters of this action. 
 * @param {string} textAreaName - Name of the textarea on the page that the event listens to. Can be changed under "Common".
 * @param {string} buttonName - Name of the button on the page that should be clicked. Can be changed under "Common".
 * @param {boolean} submitOnEnter - Button will be clicked when the users enters the enter key on their keyboard.
 * @param {boolean} submitOnShiftEnter - Button will be clicked when the users enters the enter and shift key on their keyboard.
 * @returns {Promise.<void>}
 */
export async function Textarea_ExecuteButtonAction(textAreaName, buttonName, submitOnEnter, submitOnShiftEnter) {
	// BEGIN USER CODE
	try {
		//Validations
		if (!textAreaName || textAreaName.trim().length === 0) {
			throw new Error("TextAreaName is required.")
		}
		if (!buttonName || buttonName.trim().length === 0) {
			throw new Error("ButtonName is required.")
		}

		//Get textAreaElement. The mx-name is part of the ID.
		const textAreaElement = document.querySelector('textarea[id*="' + textAreaName + '"]');
		if (textAreaElement === null) {
			throw new Error("Textarea with that name could not be found.")
		}

		//Add Event Listener that clicks the button
		textAreaElement.addEventListener('keydown', function(event) {
			if (event.key === 'Enter') {
				if (event.shiftKey) {
					if (submitOnShiftEnter) {
						clickButton(buttonName);			
					}
				} else if (!event.ctrlKey && !event.altKey && !event.metaKey) {
					if (submitOnEnter) {
						clickButton(buttonName);
					}
				}
			}
		});
	}
	catch (err) {
		console.error(err)
	} 
	// END USER CODE
}
