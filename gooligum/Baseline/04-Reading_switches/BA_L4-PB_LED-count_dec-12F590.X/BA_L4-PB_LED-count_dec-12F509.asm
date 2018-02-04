;************************************************************************
;                                                                       *
;   Filename:      BA_L4-PB_LED_count_dec-12F509.asm                  *
;   Date:          02/03/2018                                           *
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
;   Description:    Lesson 4, p. 13a                                    *
;                                                                       *
;   Demonstrates how to debounce a switch using by proceeding only      *
;   when the switch state is the same for a given number of samples.    *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = toggling LED                                              *
;       GP3 = sw1 state                                                 *
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
	UDATA_SHR
sGPIO   res 1                     ; shadow copy of GPIO
	
;***** VARIABLE DEFINITIONS *****************************************************
	UDATA
dc1     res 1                     ; delay counter
db_cnt	res 1                     ; debounce counter
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x3FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration 
		
;***** MAIN PROGRAM ************************************************************
	
;***** Initialization
start	
	movlw   b'111101'         ; configure GP1 (only) as an output
	tris    GPIO
	
;***** Main loop
main_loop
	banksel dc1
	; wait for button press
db_dn   clrf    dc1               ; zero the delay counter
	clrf    db_cnt            ; zero the bounce counter
dn_dly  incfsz  dc1,F             ; delay 256 x 3 uS = 768 uS
	goto    dn_dly
	btfsc   GPIO,3            ; sample GP3; (open -> 5V, up)
	goto    db_dn             ; button bounced; restart debounce
	
	incf	db_cnt,F          ; increment sample count
	movlw   .13               ; 13 samples x 768 uS/sample = 9.98 mS
	xorwf   db_cnt,W          ; calc if we've had 13 consecutive same samples
	btfss	STATUS,Z          ; check if we've had 13 consecutive same samples
	goto    dn_dly            ; go to next sample
	
	; toggle the LED
	movf    sGPIO,W
	xorlw   b'00000010'       ; toggle GP1 bit
	movwf   sGPIO             ; write to shadow
	movwf   GPIO              ; write to port
	
	; wait for button release
db_up   clrf    dc1               ; zero the delay counter
	clrf    db_cnt            ; zero the bounce counter
up_dly  incfsz  dc1,F             ; delay 256 x 3 uS = 768 uS
	goto    dn_dly
	btfss   GPIO,3            ; sample GP3; (closed -> 0V, down)
	goto    db_up             ; button bounced; restart debounce
	
	incf	db_cnt,F          ; increment sample count
	movlw   .13               ; 13 samples x 768 uS/sample = 9.98 mS
	xorwf   db_cnt,W          ; calc if we've had 13 consecutive same samples
	btfss	STATUS,Z          ; check if we've had 13 consecutive same samples
	goto    up_dly            ; go to next sample
	
	; repeat forever
	goto    main_loop         ; repeat forever

	END
