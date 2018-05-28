;************************************************************************
;                                                                       *
;   Filename:      BA_L7-Sleep_LED_on-12F509.asm                 *
;   Date:          05/26/2018                                           *
;   File Version:  1.1                                                  *
;                                                                       *
;   Author:        Mike Rocha                                           *
;   Company:       RoCo, Inc.                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Architecture:  Baseline PIC                                         *
;   Processor:     12F508/509                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Files required:                                                     *
;                                                                       *
;************************************************************************
;                                                                       *
;   Description:    Lesson 7, p. 1-2b                                *
;                                                                       *
;   Tuena on LED on GP1, waits fpr button press, then enters sleep mode *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = indicator LED                                             *
;       GP3 = pushbutton switch (active low)                            *
;                                                                       *
;************************************************************************


;	list     p=12F509             ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
;	EXTERN   delay10xW_R          ; W x 10 mS delay

;***** CONFIGURATION ************************************************************
; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.
	#define  DEBUG
	
	IFDEF DEBUG
	    ; no ext reset, no code protect, no watchdog, int RC clock
	    __CONFIG   _MCLRE_OFF & _CP_OFF & _WDT_OFF & _IntRC_OSC
	ELSE
	    ; no ext reset, yes code protect, no watchdog, int RC clock
	    __CONFIG   _MCLRE_OFF & _CP_ON & _WDT_OFF & _IntRC_OSC
	ENDIF
	    
;***** PIN ASSIGNMENTS **********************************************************
	constant REV='B'
	
	IF REV=='A'
	    #define  LED     GPIO,0       ; LED assigned to GP0
	    constant nLED=0
	ENDIF
	IF REV=='B'
	    #define  LED     GPIO,1       ; LED assigned to GP1
	    constant nLED=1
	ENDIF
	
	#define BUTTON  GPIO,3            ; pushbutton assigned to GP3

;***** MACROS *******************************************************************
; ---- This macro remains unused in this program
; Debounce switch on given input port,pin
; Waits for switch to be 'high' continuously for 10 mS
;
; Uses:       TMR0                Assumes: TMR0 configured for 256 uS/tick
;
DbnceHi MACRO   port,pin
      local     start,wait,DEBOUNCE
      variable  DEBOUNCE=(.10*.1000)/.256 ; debounce count = 10*1000uS / 256uS/tick
       
        pagesel $
start
	clrf    TMR0              ; set TMR0 to zero
wait
	; sample the switch input on GP3
	btfss   BUTTON            ; check if switch is up
	goto    start             ; if not, then restart debounce
	
	; now do the debounce timing delay
	movfw   TMR0
	xorlw	DEBOUNCE          ; debounce ticks x 256 uS/tick
	btfss   STATUS,Z
	goto    wait
	ENDM
	
;***** VARIABLE DEFINITIONS *****************************************************
        UDATA
sGPIO   res 1                     ; GPIO shadow register
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x3FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration
	pagesel start
	goto    start             ; jump to main code
	
;***** SUBROUTINE VECTORS ******************************************************
;;;delay10xW                         ; W x 10 mS delay
	;;;pagesel delay10xW_R
	;;;goto    delay10xW_R
		
;***** MAIN PROGRAM ************************************************************
MAIN    CODE
	
;***** Initialization
start	
        ; configure ports
	;;;movlw   b'111101'         ; configure GP1 as output
	movlw   ~(1<<nLED)        ; configure LED output
	tris    GPIO

	; turn off LED
	bsf LED
	
;***** Main loop
main_loop
	; wait for button press
wait_lo btfsc   BUTTON
	goto    wait_lo
	
	; toggle the the GP1 LED
	bcf     LED
	sleep                     ; enter sleep mode
	
	; repeat forever
	goto    $                 ; (this instruction should never sun
	END
