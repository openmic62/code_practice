;************************************************************************
;                                                                       *
;   Filename:      BA_L5-Timer_debounce-12F509.asm                  *
;   Date:          02/05/2018                                           *
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
;   Description:    Lesson 5, p. 11-13a                                 *
;                                                                       *
;   Demonstrates use of Timer0 to implement debounce countiung algorithm*
;                                                                       *
;   Toggles LED when pushbutton is pressed and released                 *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = "button pressed" indicator LED                            *
;       GP3 = pushbutton switch (active low)                            *
;                                                                       *
;************************************************************************


;	list     p=12F509             ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
;	EXTERN   delay10xW_R          ; W x 10 mS delay

	;***** CONFIGURATION
	; ext reset, no code protect, no watchdog, int RC clock
	__CONFIG   _MCLRE_OFF & _CP_OFF & _WDT_OFF & _IntRC_OSC

; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.

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
	movlw   b'111101'         ; configure GP1 as output
	tris    GPIO
	; configure timer
        movlw   b'11010101'       ; configure Timer0
	        ; --0-----            timer mode (TOCS = 0)
		; ----0---            assign prescaler to Timer0 (PSA = 0)
		; -----101            set prescaler to 1:64 (PS<2:0> = 101)
	option                    ;   -> increment every 64 uS
	
	; turn off LED
	banksel sGPIO
	clrf    sGPIO
	clrf    GPIO
	
;***** Main loop
main_loop
dn_dbnc
	clrf    TMR0              ; set TMR0 to zero
dn_tmr0		
	; sample the switch input on GP3
	btfsc   GPIO,3            ; check if switch is down
	goto    dn_dbnc           ; if not, then restart debounce
	
	; now do the debounce timing delay
	movfw   TMR0
	xorlw   .157              ; 157 x 64 uS = 10 mS
	btfss   STATUS,Z
	goto    dn_tmr0
	
	; toggle the the GP1 LED
	movfw   sGPIO             ; get the GP1 LED state
	xorlw   b'00000010'       ; toggle the GP1 bit
	movwf   sGPIO             ; save tne new state
	movwf   GPIO              ; set the new GP1 state
	
up_dbnc
	clrf    TMR0              ; set TMR0 to zero
up_tmr0
	; sample the switch input on GP3
	btfss   GPIO,3            ; check if switch is up
	goto    up_dbnc           ; if not, then restart debounce
	
	; now do the debounce timing delay
	movfw   TMR0
	xorlw   .157              ; 157 x 64 uS = 10 mS
	btfss   STATUS,Z
	goto    up_tmr0
	
	; repeat forever
	goto    main_loop         ; repeat forever

	END
